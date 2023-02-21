"""
2021S = 1
2021F = 2
2022S = 3
2022F = 4
2023S = 5
Blocks 5-8 = 5678 etc.
Block H = 9
"""

"""
# Program for demonstration of one hot encoding
import numpy as np
import pandas as pd
import csv

# import the data required
data = pd.read_csv("classDataTest.csv")
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
one_hot_encoded_data.to_csv('one_hot_encoded_dataTest.csv', index=False)
"""

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
# Import necessary libraries
import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.linear_model import LinearRegression

# Load the one_hot_encoded_data.csv file into a Pandas DataFrame
data = pd.read_csv('one_hot_encoded_dataTest.csv')
print(data.isnull().sum())
# Replace missing values with the mean of the column
data.fillna(data.mean(), inplace=True)


# Split the data into training and testing sets
X = data.iloc[:, :-1]  # input features
y = data.iloc[:, -1]   # target variable
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)


# Create a linear regression model and fit it to the training data
model = LinearRegression()
model.fit(X_train, y_train)


# Load the predictClass.csv file into a new Pandas DataFrame
new_data = pd.read_csv('predictClass.csv')

# Use the model to make predictions on the new data
predictions = model.predict(new_data[['Block', 'Year', 'Demand', 'Limit', 'Waitlist']])

mse = mean_squared_error(y_test, y_pred)
rmse = np.sqrt(mse)

print('RMSE:', rmse)
print(predictions)
"""