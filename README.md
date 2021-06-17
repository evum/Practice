<pre>
                                ########  ##       ##              ##
                                ##     ## ##       ##    ##        ##
                                ##     ## ##       ##    ##        ##
                       **$**    ##     ## ##       ##    ##        ##    **$**
                                ##     ## ##       ######### ##    ##
                                ##     ## ##             ##  ##    ##
                                ########  ########       ##   ######
              .   :::: :   :    :   :     : ::::  :     ::::    :::::  :::: ::::  :::::   .
              .   :    :   :   : :  ::   :: :   : :     :       :   :  :    :   : :   :   .
              .   :     : :   :   : : : : : :   : :     :       :   :  :    :   : :   :   .
              .   :::    :    :   : :  :  : ::::  :     :::     :::::  :::  ::::  :   :   .
              .   :     : :   ::::: :     : :     :     :       :  :   :    :     :   :   .
              .   :    :   :  :   : :     : :     :     :       :   :  :    :     :   :   .
              .   :::: :   :  :   : :     : :     ::::: ::::    :    : :::: :     :::::   .
</pre>

## Introduction
The **Eclipse Deeplearning4J** (DL4J) ecosystem is a set of projects intended to support all the needs of a JVM based deep learning application. This means starting with the raw data, loading and preprocessing it from wherever and whatever format it is in to building and tuning a wide variety of simple and complex deep learning networks.

The DL4J stack comprises of:
- **DL4J**: High level API to build MultiLayerNetworks and ComputationGraphs with a variety of layers, including custom ones. Supports importing Keras models from h5, including tf.keras models (as of 1.0.0-beta7) and also supports distributed training on Apache Spark
- **ND4J**: General purpose linear algebra library with over 500 mathematical, linear algebra and deep learning operations. ND4J is based on the highly-optimized C++ codebase LibND4J that provides CPU (AVX2/512) and GPU (CUDA) support and acceleration by libraries such as OpenBLAS, OneDNN (MKL-DNN), cuDNN, cuBLAS, etc
- **SameDiff** : Part of the ND4J library, SameDiff is our automatic differentiation / deep learning framework. SameDiff uses a graph-based (define then run) approach, similar to TensorFlow graph mode. Eager graph (TensorFlow 2.x eager/PyTorch) graph execution is planned. SameDiff supports importing TensorFlow frozen model format .pb (protobuf) models. Import for ONNX, TensorFlow SavedModel and Keras models are planned. Deeplearning4j also has full SameDiff support for easily writing custom layers and loss functions.
- **DataVec**: ETL for machine learning data in a wide variety of formats and files (HDFS, Spark, Images, Video, Audio, CSV, Excel etc)
- **Arbiter**: Library for hyperparameter search
- **LibND4J** : C++ library that underpins everything. For more information on how the JVM acceses native arrays and operations refer to [JavaCPP](https://github.com/bytedeco/javacpp)

All projects in the DL4J ecosystem support Windows, Linux and macOS. Hardware support includes CUDA GPUs (10.0, 10.1, 10.2 except OSX), x86 CPU (x86_64, avx2, avx512), ARM CPU (arm, arm64, armhf) and PowerPC (ppc64le).

## Prerequisites
This repository contains dl4j-examples from dl4j library, and my example with bike classification neural network.