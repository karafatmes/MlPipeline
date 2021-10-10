from pyspark.ml.feature import StandardScaler
from pyspark.ml.linalg import Vectors
from pyspark.sql import SparkSession


def create_pipeline():
    session = start_session()

    dataframes = create_dataframes(session)
    create_model(dataframes)

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

def create_dataframes(session):
    # Create the dummy data
    features_df = session.createDataFrame([
        (1, Vectors.dense([10.0, 10000.0, 1.0]),),
        (2, Vectors.dense([20.0, 30000.0, 2.0]),),
        (3, Vectors.dense([30.0, 40000.0, 3.0]),),

    ], ["id", "features"])

    return features_df

def create_model(features_df):
    # Apply the StandardScaler model
    features_stand_scaler = StandardScaler(inputCol="features", outputCol="sfeatures", withStd=True, withMean=True)
    stmodel = features_stand_scaler.fit(features_df)
    stand_sfeatures_df = stmodel.transform(features_df)
    stand_sfeatures_df.show()


if __name__ == "__main__":
    create_pipeline()