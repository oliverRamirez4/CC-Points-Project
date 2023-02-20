"""
# Program for demonstration of one hot encoding

import numpy as np
import pandas as pd
import csv

# import the data required
data = pd.read_csv("classData.csv")
print(data.head())

#Id,Block,Year,Demand,Limit,Waitlist,MinPoint
print(data['Block'].unique())
print(data['Year'].unique())
print(data['Demand'].unique())
print(data['Limit'].unique())
print(data['Waitlist'].unique())
print(data['MinPoint'].unique())

data['Block'].value_counts()
data['Year'].value_counts()
data['Demand'].value_counts()
data['Limit'].value_counts()
data['Waitlist'].value_counts()

one_hot_encoded_data = pd.get_dummies(data, columns = ['Block', 'Year', 'Demand', 'Limit', 'Waitlist'])


# move MinPoint column to the back
MinPoint_col = one_hot_encoded_data.pop('MinPoint')
one_hot_encoded_data.insert(len(one_hot_encoded_data.columns), 'MinPoint', MinPoint_col)

# write the one hot encoded data to a new CSV file
one_hot_encoded_data.to_csv('one_hot_encoded_data.csv', index=False)
"""

"""
import pandas as pd
from sklearn.linear_model import LinearRegression
from sklearn.preprocessing import OneHotEncoder

# import the data required
data = pd.read_csv("classData.csv")

# create the one-hot encoder
encoder = OneHotEncoder(sparse=False)

# encode the categorical features using the one-hot encoder
encoded_data = pd.DataFrame(encoder.fit_transform(data[['Block', 'Year', 'Demand', 'Limit', 'Waitlist']]))

# add the numerical features to the encoded data
encoded_data = pd.concat([encoded_data, data[['MinPoint']]], axis=1)

# split the data into training and testing sets
X_train = encoded_data.sample(frac=0.8, random_state=1)
X_test = encoded_data.drop(X_train.index)

# define the target variable and features
y_train = X_train['MinPoint']
X_train = X_train.drop(['MinPoint'], axis=1)

y_test = X_test['MinPoint']
X_test = X_test.drop(['MinPoint'], axis=1)

# create the linear regression model and fit it to the training data
model = LinearRegression()
model.fit(X_train, y_train)

# use the model to make predictions on the testing data
y_pred = model.predict(X_test)

# evaluate the performance of the model
mse = ((y_test - y_pred) ** 2).mean()
print("Mean squared error:", mse)
"""


import pandas as pd
from sklearn.linear_model import LinearRegression
from sklearn.preprocessing import OneHotEncoder

# import the data required
data = pd.read_csv("classData.csv")

# create the one-hot encoder
encoder = OneHotEncoder(sparse=False)

# encode the categorical features using the one-hot encoder
encoded_data = pd.DataFrame(encoder.fit_transform(data[['Block', 'Year', 'Demand', 'Limit', 'Waitlist']]))

# add the numerical features to the encoded data
encoded_data = pd.concat([encoded_data, data[['MinPoint']]], axis=1)

# split the data into training and testing sets
X_train = encoded_data.sample(frac=0.8, random_state=1)
X_test = encoded_data.drop(X_train.index)

# define the target variable and features
y_train = X_train['MinPoint']
X_train = X_train.drop(['MinPoint'], axis=1)

y_test = X_test['MinPoint']
X_test = X_test.drop(['MinPoint'], axis=1)

# create the linear regression model and fit it to the training data
model = LinearRegression()
model.fit(X_train, y_train)

# load the new data to predict
new_data = pd.read_csv("predictClass.csv")

# encode the categorical features using the one-hot encoder
encoded_new_data = pd.DataFrame(encoder.transform(new_data[['Block', 'Year', 'Demand', 'Limit', 'Waitlist']]))

# add the numerical features to the encoded data
encoded_new_data = pd.concat([encoded_new_data, new_data[['MinPoint']]], axis=1)

# drop the target variable from the new data
X_new = encoded_new_data.drop(['MinPoint'], axis=1)

# use the model to make predictions on the new data
y_pred = model.predict(X_new)

# print the predicted values
print("Predicted MinPoint values for new data:")
print(y_pred)


"""
import pandas as pd
from sklearn.linear_model import LinearRegression
from sklearn.preprocessing import OneHotEncoder

# import the data required
data = pd.read_csv("classData.csv")

# create the one-hot encoder
encoder = OneHotEncoder(sparse=False)

# encode the categorical features using the one-hot encoder
encoded_data = pd.DataFrame(encoder.fit_transform(data[['Block', 'Year', 'Demand', 'Limit', 'Waitlist']]))

# add the numerical features to the encoded data
encoded_data = pd.concat([encoded_data, data[['MinPoint']]], axis=1)

# split the data into training and testing sets
X_train = encoded_data.sample(frac=0.8, random_state=1)
X_test = encoded_data.drop(X_train.index)

# define the target variable and features
y_train = X_train['MinPoint']
X_train = X_train.drop(['MinPoint'], axis=1)

y_test = X_test['MinPoint']
X_test = X_test.drop(['MinPoint'], axis=1)

# impute missing values in the new data
new_data = pd.read_csv("predictClass.csv")
new_data = new_data.fillna(new_data.mean())

# encode the categorical features using the same one-hot encoder object
# first, extract the categorical columns that are present in both the training and test data
common_cols = set(data.columns) & set(new_data.columns) - set(['MinPoint'])
categorical_cols = [col for col in common_cols if col in ['Block', 'Year', 'Demand', 'Limit', 'Waitlist']]
encoded_new_data = pd.DataFrame(encoder.transform(new_data[categorical_cols]))

# add the numerical features to the encoded data
encoded_new_data = pd.concat([encoded_new_data, new_data[['MinPoint']]], axis=1)

# drop the target variable from the new data
X_new = encoded_new_data.drop(['MinPoint'], axis=1)

# use the model to make predictions on the new data
y_pred = model.predict(X_new)

# print the predicted values
print("Predicted MinPoint values for new data:")
print(y_pred)
"""