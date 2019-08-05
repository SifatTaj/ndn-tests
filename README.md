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
  
### Getting Started
There are two main classes, `memeProducer.java` and **Consumer**. As the names suggest, one is a **Producer** class and other is a **Consumer** class. The **Producer** application should run on server side and **Consumer** application on client side. The **Producer** app sends a jpeg file named `meme.jpg` from the project's root directory. The **Consumer** app receives the file and saves it. the default save location is hard coded in `memeConsumer.java`. if you are using *macOS 10.10* or later, it will be saved in your *Pictures* directory. For other users, you have to change the `saveLocation` parameter in `memeConsumer.java`.
