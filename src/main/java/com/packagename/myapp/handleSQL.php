

<?php
//this code snippet is very sensitive dont try modify or edit.
error_reporting(E_ALL ^ E_WARNING);

//retrieve posted informations
foreach ($_POST as $key => $value) {
    switch ($key) {
        case 'DB_HOST':
            $dbhost = $value;	//host name
            break;
        case 'DB_NAME':
            $dbname = $value;	//database name
            break;
        case 'DB_USERNAME':
            $dbuser = $value;	//username
            break;
        case 'DB_PASSWORD':
            $dbpass = $value;	//password
            break;
        case 'stmt':
            $stmt = $value;		//mySQL statement
            break;
        case 'SQL':
            $SQL = $value;		//Flag
            break;
        default:
            break;
    }
}

//Establish connection with database
$conn = mysqli_connect($dbhost, $dbuser, $dbpass,$dbname);

//checking connection
if($conn){
	if($SQL=="query"){
		//execute query
		$result=$conn->query($stmt);
		if($result){
			//packaging starts
			$rows=mysqli_num_rows($result);
			$columns=mysqli_num_fields($result);
			
			echo $rows.",".$columns."*/*";							//packaging of number of rows and columns
			
			while ($property = mysqli_fetch_field($result)) {		//column names packaging
				echo $property->name."*/*";
			}
			
			while($row = mysqli_fetch_row($result)){				//quered data packaging
				$j=$columns;
				for($i=0;$i<$j;$i++){
					echo $row[$i];
					if($i==$j-1){
						echo "*/*";	
					}else{
						echo "/*/";
					}
				}
			}
			//packaging ends
		}
		else{
			echo "/*error*/:".mysqli_error($conn);	//echo error message if error occurred
		}
	}else{			
		//execute update command
		$result=mysqli_query($conn,$stmt);
		if($result){
			echo $result;							//echo result if executed
		}
		else{
			echo "/*error*/:".mysqli_error($conn);	//echo error message if error occurred
		}
	}
	$conn->close(); //close connection
}else{
	//kill connection and echo error message
    die ("/*error*/:".mysqli_connect_error());
}
?>