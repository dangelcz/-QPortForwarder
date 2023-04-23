# QPortForwarder

Simple TCP port forwarding application.

![Application image](/doc/qportforwarder_app.png)

---
## What is it good for? 

It creates forwarding handler between **local device** and some **target service**.

For example there is some web server running on **some.domain.example:80**
and we want to traverse communication through our host on port **8888**.

Start the program and start forwarding with following parameters:
`127.0.0.1 8888 some.domain.example 80`

This will open connection on port **8888** of our source device on all interfaces (**0.0.0.0** is used when no exact ip is specified) 
, and will forward **some.domain.example:80**.

Anyone in our network can now access **some.domain.example:80** by accessing our ip and port **8888**.

Application remembers its settings in **session.json** file. It will autosave when the application exists.

---
## Minimal requirements
JRE 8

---
## Console usage
Application can be run even from command line interface.
In current state, only one forwarding rule can be run at a time.

Start application from command line with standard `java -jar QPortForwarder.jar` and add parameters.

### Parameters description.
1. `forward`
   1. Forwarding command with following parameters options
      1. `localPort targetPort`
         1. Creates port forwarding handler between `0.0.0.0:port` and `localhost:port`
      2. `localPort targetIp targetPort`
         1. Creates port forwarding handler between `0.0.0.0:port` and `targetIp:port`
      3. `localIp localPort targetIp targetPort`
         1. Creates port forwarding handler from `localIp:port` to target `targetIp:port`
         2. This is handy for example when local pc has multiple network interfaces and we need to provide forwarding between them.
   2. Examples:
      1. `forward 8888 8080` will create forwarding handler between **0.0.0.0:8888** and **127.0.0.1:8080**
      2. `forward 8888 192.168.10.10 8080` will create forwarding handler from **0.0.0.0:8888** to ip **192.168.10.10:8080** 
      3. `forward 192.168.10.5 8888 10.0.0.5 8080` will create forwarding handler from local ip & port **192.168.10.5:8080** to **10.0.0.5:8888**
2. `help`
   1. Prints description of all available commands




