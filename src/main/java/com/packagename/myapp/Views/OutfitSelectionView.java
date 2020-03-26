package com.packagename.myapp.Views;

import com.packagename.myapp.Components.TopBar;
import com.packagename.myapp.Services.Interfaces.IClothesStorage;
import com.packagename.myapp.Services.Interfaces.IImageStorage;
import com.packagename.myapp.Utils.ClothingOptions;
import com.packagename.myapp.Utils.SessionData;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.HtmlComponent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Map;

@Route(value="outfit")
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class OutfitSelectionView extends VerticalLayout {

    private IClothesStorage clothesStorageService;
    private IImageStorage imageStorageService;

    private ArrayList<Div> pages;
    private int currentPage;

    private Map<String,Map<String,String>> clothes;
    private String[] outfitKeys;

    public OutfitSelectionView(@Autowired IClothesStorage clothesStorageService, @Autowired IImageStorage imageStorageService) {
        this.clothesStorageService = clothesStorageService;
        this.imageStorageService = imageStorageService;

        setup();
    }

    public void setup() {
        super.removeAll();

        pages = new ArrayList<Div>();
        currentPage = 0;

        clothes = clothesStorageService.getClothes("","", SessionData.getAttribute("Username"));
        outfitKeys = new String[3];

        //Top selection screen
        Div pickTop = new Div();
        pickTop.add(new Html("<h2>Pick a top:</h2>"));
        pickTop.add(new HtmlComponent("br"));
        FlexLayout topPictureArea = new FlexLayout();
        topPictureArea.setWrapMode(FlexLayout.WrapMode.WRAP);

        for(String clothId : clothes.keySet()){
            String item = clothes.get(clothId).get("ClothModel");
            if(ClothingOptions.validClothingType(ClothingOptions.topTypes,item)){
                topPictureArea.add(createClothingItem(clothId,clothes.get(clothId)));
            }
        }
        pickTop.add(topPictureArea);

        //Bottom selection screen
        Div pickBottom = new Div();
        Button bottomBack = new Button("Back",e -> {remove(pages.get(currentPage));currentPage--;add(pages.get(currentPage));});
        bottomBack.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        pickBottom.add(bottomBack);
        pickBottom.add(new Html("<h2>Pick a bottom:</h2>"));
        pickBottom.add(new HtmlComponent("br"));
        FlexLayout bottomPictureArea = new FlexLayout();
        bottomPictureArea.setWrapMode(FlexLayout.WrapMode.WRAP);

        for(String clothId : clothes.keySet()){
            String item = clothes.get(clothId).get("ClothModel");
            if(ClothingOptions.validClothingType(ClothingOptions.bottomTypes,item)){
                bottomPictureArea.add(createClothingItem(clothId,clothes.get(clothId)));
            }
        }
        pickBottom.add(bottomPictureArea);


        //Shoes selection screen
        Div pickShoes = new Div();
        Button shoesBack = new Button("Back",e -> {remove(pages.get(currentPage));currentPage--;add(pages.get(currentPage));});
        shoesBack.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        pickShoes.add(shoesBack);
        pickShoes.add(new Html("<h2>Pick shoes:</h2>"));
        pickShoes.add(new HtmlComponent("br"));
        FlexLayout shoesPictureArea = new FlexLayout();
        shoesPictureArea.setWrapMode(FlexLayout.WrapMode.WRAP);

        for(String clothId : clothes.keySet()){
            String item = clothes.get(clothId).get("ClothModel");
            if(ClothingOptions.validClothingType(ClothingOptions.shoeTypes,item)){
                shoesPictureArea.add(createClothingItem(clothId,clothes.get(clothId)));
            }
        }
        pickShoes.add(shoesPictureArea);

        pages.add(pickTop);
        pages.add(pickBottom);
        pages.add(pickShoes);

        add(new TopBar(),pages.get(currentPage));
    }

    public void createFinalOutfitScreen(){
        Div showOutfit = new Div();
        showOutfit.addClassName("centered-content");
        showOutfit.add(new Html("<h2 class=\"centered-text\">Outfit:</h2>"));
        showOutfit.add(new HtmlComponent("br"));
        Image topImage = new Image(clothes.get(outfitKeys[0]).get("ImageLink"),"");
        topImage.setWidth("150px");
        topImage.setHeight("150px");
        showOutfit.add(topImage);;
        showOutfit.add(new HtmlComponent("br"));
        Image bottomImage = new Image(clothes.get(outfitKeys[1]).get("ImageLink"),"");
        bottomImage.setWidth("150px");
        bottomImage.setHeight("150px");
        showOutfit.add(bottomImage);;
        showOutfit.add(new HtmlComponent("br"));
        Image shoeImage = new Image(clothes.get(outfitKeys[2]).get("ImageLink"),"");
        shoeImage.setWidth("150px");
        shoeImage.setHeight("150px");
        showOutfit.add(shoeImage);

        //Something like this:
        //String colorsMatchComment = neuralNetworkService.getResponse(clothes.get(outfitKeys[0]),clothes.get(outfitKeys[1]),clothes.get(outfitKeys[2]));
        //showOutfit.add(new Text(colorsMatchComment));

        showOutfit.add(new HtmlComponent("br"));
        showOutfit.add(new HtmlComponent("br"));
        Button restart = new Button("Restart");
        restart.addClickListener(e -> {restart.setEnabled(false);setup();});
        restart.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        showOutfit.add(restart);

        pages.add(showOutfit);
    }

    public Div createClothingItem(String imageId, Map<String,String> imageAttributes){
        Div closetItem = new Div();

        Image image = new Image(imageAttributes.get("ImageLink"),"");
        image.setWidth("300px");
        image.setHeight("300px");
        closetItem.add(image);
        closetItem.add(new HtmlComponent("br"));
        Button select = new Button("Select");
        select.addClickListener(e->{
            remove(pages.get(currentPage));
            outfitKeys[currentPage] = imageId;
            currentPage++;
            if(currentPage == 3){
                createFinalOutfitScreen();
            }
            add(pages.get(currentPage));
        });
        closetItem.add(select);

        return closetItem;
    }
}
