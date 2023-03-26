# QPortForwarder

QPortForwarder serves for simple TCP port forwarding.

---
## What is it good for? 

It creates forwarding handler between **local device** and some **target service**.

For example there is some web server running on **some.domain.example:80**
and we want to traverse communication through our pc on port **8888**.

Start the program with following parameters:
`java -jar QPortForwarder.jar 8888 some.domain.example 80`

This will open connection on port **8888** of our source device on all interfaces (**0.0.0.0** is used when no exact ip is specified) 
, and will forward **some.domain.example:80**.

Anyone in our network can now access **some.domain.example:80** by accessing our ip and port **8888**.


---
## Minimal requirements
JRE 8

---
## Usage
Current application version supports only console commands mode.
Future release may contain simple gui for easier handling.

Start application from command line with standard `java -jar QPortForwarder.jar` and add parameters.

### Parameters description.
1. `forward` (or empty)
   1. Forwarding command with following parameters options
      1. `localPort targetPort`
         1. Creates port forwarding handler on `localhost`
      2. `localPort targetIp targetPort`
         1. Creates port forwarding handler between `localhost:port` and `targetIp:port`
      3. `localIp localPort targetIp targetPort`
         1. Creates port forwarding handler from `localIp:port` to target `targetIp:port`
         2. This is handy for example when local pc has multiple network interfaces and we need to provide forwarding between them.
   2. Examples:
      1. `forward 8080 8888` will create forwarding handler between **8080** to **8888** on **localhost**.
      2. `forward 8080 192.168.10.10 8888` will create forwarding handler from **localhost:8080** to ip **192.168.10.10:8888** 
      3. `forward 192.168.10.5 8080 10.0.0.5 8888` will create forwarding handler from local ip & port **192.168.10.5:8080** to **10.0.0.5:8888**
2. `help`
   3. Prints description of all available commands


