import os

from pyspark.ml.classification import LogisticRegression
from pyspark.sql import SparkSession
from pyspark.sql.types import StructType, StructField, IntegerType, StringType
from IPython.display import display
from pyspark.ml.feature import OneHotEncoder, StringIndexer, VectorAssembler
from pyspark.ml import Pipeline
import pandas as pd

def main():

    # Start session with PySpark
    print("Start session")

    # First create the seesion
    session = start_session()

    preprocess_csv_files()

    # Define initial schema

    schema = StructType([
        StructField("age", IntegerType(), True),
        StructField("workclass", StringType(), True),
        StructField("fnlwgt", IntegerType(), True),
        StructField("education", StringType(), True),
        StructField("education-num", IntegerType(), True),
        StructField("marital-status", StringType(), True),
        StructField("occupation", StringType(), True),
        StructField("relationship", StringType(), True),
        StructField("race", StringType(), True),
        StructField("sex", StringType(), True),
        StructField("capital-gain", IntegerType(), True),
        StructField("capital-loss", IntegerType(), True),
        StructField("hours-per-week", IntegerType(), True),
        StructField("native-country", StringType(), True),
        StructField("salary", StringType(), True)
    ])

    #  Read data from csv file. Use dpopna method to remove Rows with null values
    train_df = session.read.csv('train.csv', header=False, schema=schema).dropna(how="all")
    #  Read data from csv file. Use dpopna method to remove Rows with null values
    test_df = session.read.csv('test.csv', header=False, schema=schema).dropna(how="all")

    print(" Data frames of training set")
    display(train_df.head(5))
    print(" Data frames of test set")
    display(test_df.head(5))

    pipeline = create_stages_of_pipeline()

    train_df = pipeline.fit(train_df).transform(train_df)
    display(train_df.head(5))
    test_df = pipeline.fit(test_df).transform(test_df)
    display(test_df.head(5))

    train_df.printSchema()

    continuous_variables = ['age', 'fnlwgt', 'education-num', 'capital-gain', 'capital-loss', 'hours-per-week']
    assembler = VectorAssembler(
        inputCols=['categorical-features', *continuous_variables],
        outputCol='features'
    )
    train_df = assembler.setHandleInvalid("skip").transform(train_df)
    test_df = assembler.setHandleInvalid("skip").transform(test_df)

    print("train dataframe before fit is ")
    display(train_df)
    print("test dataframe before fit is ")
    display(test_df)

    indexer = StringIndexer(inputCol='salary', outputCol='label')
    train_df = indexer.fit(train_df).transform(train_df)
    test_df = indexer.fit(test_df).transform(test_df)
    display(train_df.limit(10).toPandas()['label'])

    lr = LogisticRegression(featuresCol='features', labelCol='label')
    model = lr.fit(train_df)

    print("model is \n")
    display(model)

    print("train dataframe is ")
    display(train_df)
    print("test dataframe is ")
    display(test_df)

    pred = model.transform(test_df)
    display(pred.limit(10).toPandas()[['label', 'prediction']])

    print("End of session")


def start_session():

    # First create the seesion
    sparkSession = SparkSession \
        .builder \
        .master("local[2]") \
        .appName("Ml Pipeline") \
        .config("spark.executor.memory", "1g") \
        .config("spark.cores.max", "2") \
        .config("spark.sql.warehouse.dir", "/Users/sakes/Desktop/MlPipeline") \
        .getOrCreate()

    return sparkSession

def create_stages_of_pipeline():
    categorical_variables = ['workclass', 'education', 'marital-status', 'occupation', 'relationship', 'race', 'sex',
                             'native-country']
    indexers = [StringIndexer(inputCol=column, outputCol=column + "-index") for column in categorical_variables]
    encoder = OneHotEncoder(
        inputCols=[indexer.getOutputCol() for indexer in indexers],
        outputCols=["{0}-encoded".format(indexer.getOutputCol()) for indexer in indexers]
    )
    assembler = VectorAssembler(
        inputCols=encoder.getOutputCols(),
        outputCol="categorical-features"
    )
    pipeline = Pipeline(stages=indexers + [encoder, assembler])
    return pipeline

def preprocess_csv_files():

    column_names = [
        'age',
        'workclass',
        'fnlwgt',
        'education',
        'education-num',
        'marital-status',
        'occupation',
        'relationship',
        'race',
        'sex',
        'capital-gain',
        'capital-loss',
        'hours-per-week',
        'native-country',
        'salary'
    ]

    # Read first csv files
    train_df = pd.read_csv('train.csv', names=column_names)
    test_df = pd.read_csv('test.csv', names=column_names)

    train_df = train_df.apply(lambda x: x.str.strip() if x.dtype == 'object' else x)
    train_df_cp = train_df.copy()

    train_df_cp = train_df_cp.loc[train_df_cp['native-country'] != 'Holand-Netherlands']
    # save updated dataframe to csv file
    train_df_cp.to_csv('train.csv', index=False, header=False)

    test_df = test_df.apply(lambda x: x.str.strip() if x.dtype == 'object' else x)
    # save updated dataframe to csv file
    test_df.to_csv('test.csv', index=False, header=False)


if __name__ == "__main__":
    main()



