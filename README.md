# RDFFormatConverter
Small converter using rdf-translator.appspot.com API

## How to use:

From a terminal in this project's root folder:

mvn clean package

java -cp target/RDFFormatConverter-0.0.1-SNAPSHOT.jar Main **\<Path to the folder in which are the files you want to convert\>** **\<source format\>** **\<target format\>**

**Source formats**: 
  * rdfa 
  * microdata
  * xml
  * n3
  * nt
  * json-ld
  * detect
  
**Target formats**: 
  * rdfa 
  * microdata
  * pretty-xml
  * xml
  * n3
  * nt
  * json-ld
