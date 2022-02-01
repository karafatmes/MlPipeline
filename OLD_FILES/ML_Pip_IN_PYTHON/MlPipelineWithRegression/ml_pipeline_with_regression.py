from pyspark.ml import Pipeline
from pyspark.ml.classification import LogisticRegression
from pyspark.ml.feature import StringIndexer, VectorAssembler, OneHotEncoder
from pyspark.sql import SparkSession


def create_pipeline_with_regression():

    session = start_session()

    smple_data_train = session.createDataFrame([
        (2.0, 'A', 'S10', 40, 1.0),
        (1.0, 'X', 'E10', 25, 1.0),
        (4.0, 'X', 'S20', 10, 0.0),
        (3.0, 'Z', 'S10', 20, 0.0),
        (4.0, 'A', 'E10', 30, 1.0),
        (2.0, 'Z', 'S10', 40, 0.0),
        (5.0, 'X', 'D10', 10, 1.0),
    ], ['feature_1', 'feature_2', 'feature_3', 'feature_4', 'label'])
    # view the data
    smple_data_train.show()

    # fit the pipeline for the trainind data
    regression_pipeline = create_stages_of_pipeline()
    model = regression_pipeline.fit(smple_data_train)
    # transform the data
    sample_data_train = model.transform(smple_data_train)
    # view some of the columns generated
    sample_data_train.select('features', 'label', 'rawPrediction', 'probability', 'prediction').show()


def create_stages_of_pipeline():
    # define stage 1: transform the column feature_2 to numeric
    stage_1 = StringIndexer(inputCol='feature_2', outputCol='feature_2_index')
    # define stage 2: transform the column feature_3 to numeric
    stage_2 = StringIndexer(inputCol='feature_3', outputCol='feature_3_index')
    # define stage 3: one hot encode the numeric versions of feature 2 and 3 generated from stage 1 and stage 2
    stage_3 = OneHotEncoder(inputCols=[stage_1.getOutputCol(), stage_2.getOutputCol()],
                                     outputCols=['feature_2_encoded', 'feature_3_encoded'])
    # define stage 4: create a vector of all the features required to train the logistic regression model
    stage_4 = VectorAssembler(inputCols=['feature_1', 'feature_2_encoded', 'feature_3_encoded', 'feature_4'],
                              outputCol='features')
    # define stage 5: logistic regression model
    stage_5 = LogisticRegression(featuresCol='features', labelCol='label')
    # setup the pipeline
    regression_pipeline = Pipeline(stages=[stage_1, stage_2, stage_3, stage_4, stage_5])

    return regression_pipeline

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

if __name__ == "__main__":
    create_pipeline_with_regression()