The weights in default weights are the initial weights before any training.
{
  "GB": ["0.01","1.0","1.0","0.01","0.01","0.5"],
  "GR": ["2.0","2.0","0.01","0.01","0.01","0.5"],
  "WB": ["0.01","0.01","0.01","1.0","1.0","0.5"],
  "last": ["1.0","-1.0","2.0","1.0","0.2"],
  "pattern_weights": ["0.01","0.01","0.01","0.01","0.01"]
  }

Here is an explanation of what each value is a weight on. If these need to be expanded,
it can be done by modifying these defaults and then deleting the trained_weights.json.
Or just modify that file directly.

Pattern weights -> {solid, stripe, dot, plaid, icon}

Color weights:
GB, GR WB and last. Weights for when that color combination is present.
Gb -> Green blue
GR -> Green Red
WB -> White Black
last -> Final weighting for final judgement.
