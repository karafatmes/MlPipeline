from pyspark.ml import Pipeline
# create a sample dataframe
from pyspark.ml.feature import StringIndexer
from pyspark.shell import spark
from pyspark.sql import SparkSession
from pyspark.ml.feature import OneHotEncoder

def create_pipeline():

    session = start_session()

    sample_df = session.createDataFrame([
        (1, 'L101', 'R'),
        (2, 'L201', 'C'),
        (3, 'D111', 'R'),
        (4, 'F210', 'R'),
        (5, 'D110', 'C')
    ], ['id', 'category_1', 'category_2'])

    sample_df.show()

    create_stages_of_pipeline(sample_df)

def start_session():
    # Start session with PySpark
    print("Start session")

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

def create_stages_of_pipeline(sample_df):
    # define stage 1 : transform the column category_1 to numeric
    stage_1 = StringIndexer(inputCol='category_1', outputCol='category_1_index')
    # define stage 2 : transform the column category_2 to numeric
    stage_2 = StringIndexer(inputCol='category_2', outputCol='category_2_index')
    # define stage 3 : one hot encode the numeric category_2 column
    stage_3 = OneHotEncoder(inputCols=['category_2_index'], outputCols=['category_2_OHE'])
    # setup the pipeline
    pipeline = Pipeline(stages=[stage_1, stage_2, stage_3])
    # fit the pipeline model and transform the data as defined
    pipeline_model = pipeline.fit(sample_df)
    sample_df_updated = pipeline_model.transform(sample_df)
    # view the transformed data
    sample_df_updated.show()

if __name__ == "__main__":
    create_pipeline()
