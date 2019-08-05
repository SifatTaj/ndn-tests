# NDN: Testing data transfer using jNDN

### Overview
Experimentation with the jNDN library testing how to send and receive file thorugh NDN.

### Requirements
  - [NFD - Named Data Networking Forwarding Daemon](https://github.com/named-data/NFD)
      - [Guide](http://named-data.net/doc/NFD/current/INSTALL.html) for Linux and macOS users
      - [Guide](https://yoursunny.com/t/2018/NFD-on-Windows-10-WSL/) for Windows users on how to run NFD using Windows Subsystem for Linux
  - [Apache Maven](https://maven.apache.org/what-is-maven.html)
  - [jNDN Library](https://github.com/named-data/jndn)
  - Java JDK version >= 1.7
  - intelliJ IDEA (optional)
  
### Getting Started
1. First you need to run **NFD** on the machines you are planning to run the applications. You may connect to a nearby testbed but not necessary.

2. Clone and compile the **jNDN** library using **Maven** and add the library to your preferred IDE. I am assuming you are familiar with **Java** and have **JDK** installed in your system.

3. There are two main classes, `MemeProducer.java` and `MemeConsumer.java`. As the names suggest, one is a **Producer** class and other is a **Consumer** class. The **Producer** application should run on server side and **Consumer** application on client side. The **Producer** app sends a jpeg file named `meme.jpg` from the project's root directory. The **Consumer** app receives the file and saves it. The default save location is hard coded in `MemeConsumer.java`. if you are using *macOS 10.10* or later, it will be saved in your *Pictures* directory. For other users, you have to change the `saveLocation` parameter in `MemeConsumer.java`. I have created `SendFile.java` and `ReceiveFile.java` as separate modules so that they can be used to send and receive any type of data in the future. 
