Spring Cloud AWS Reference App
==============================

Run it on your own AWS environment
----------------------------------
If you want to start this application on your own AWS environment you just need to do the following steps:
* Go to the CloudFormation console.
* Create a new stack
    * Choose "Upload a template to Amazon S3".
    * Upload the _AwsSampleStack.template_ file.
* Enter a value of your choice for the _RdsPassword_ parameter.

The stack needs a while to start (around 15 to 20 minutes). Once it's complete, you can copy the public DNS address
of the EC2 instance created and open it in your browser with port 8080. For example http://ec2-54-72-102-202.eu-west-1.compute.amazonaws.com:8080.
 
