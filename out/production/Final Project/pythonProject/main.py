"""
2021S = 1
2021F = 2
2022S = 3
2022F = 4
2023S = 5
Blocks 5-8 = 5678 etc.
Block H = 9
"""


import pandas as pd
from sklearn.preprocessing import OneHotEncoder

# Load the data from the CSV file
data = pd.read_csv("classData.csv")

# Use one hot encoding on the Id and block columns, and the new Demand_bin column
cols_to_encode = ['Block','Year','Demand','Limit','Waitlist']
encoder = OneHotEncoder(sparse=False)
encoded_cols = encoder.fit_transform(data[cols_to_encode])
# Get the feature names for the encoded columns
feature_names = encoder.get_feature_names_out(cols_to_encode)

# Concatenate the encoded columns with the Waitlist and MinPoint columns and reorder the columns
new_data = pd.concat([pd.DataFrame(encoded_cols, columns=feature_names), data[['MinPoint']]], axis=1)

# Save the new dataframe to a CSV file with column names
new_data.to_csv("oneHotEncodedDataTest.csv", index=False, header=list(feature_names) + ['MinPoint'])

data_predict = pd.read_csv("predictClass.csv")

cols_to_encode = ['Block','Year','Demand','Limit','Waitlist']

encoded_cols = encoder.transform(data_predict[cols_to_encode])

# Concatenate the encoded columns with the Waitlist and MinPoint columns and reorder the columns
new_predict_data = pd.concat([pd.DataFrame(encoded_cols, columns=feature_names)], axis=1)

# Save the new dataframe to a CSV file with column names
new_predict_data.to_csv("oneHotEncodedPredictTest.csv", index=False, header=list(feature_names))



import pandas as pd
from sklearn.linear_model import LinearRegression
from sklearn.linear_model import Ridge

# Load the training data from the CSV file
train_data = pd.read_csv("oneHotEncodedDataTest.csv")

# Load the test data to be predicted
test_data = pd.read_csv("oneHotEncodedPredictTest.csv")

# Get the feature columns and the target column (MinPoint) for the training data
train_features = train_data.drop('MinPoint', axis=1)
train_target = train_data['MinPoint']

# Fit a linear regression model to the training data
model = Ridge().fit(train_features, train_target)

# Predict the MinPoint values for the test data
test_data['MinPoint'] = model.predict(test_data)

# Print the test data with the predicted MinPoint values

test_data.to_csv("predictedClassPoint.csv")
print(test_data)

coef = model.coef_
intercept = model.intercept_

print("Coefficients:", coef)
print("Intercept:", intercept)