# playing-with-multicast-java

This is a sample repository of how to implement multicast communication in Java. 

# description

The ``SenderThread`` class sends the String ``hi`` every 5 seconds in a DatagramPacket addressed to 225.4.5.6:5000 (randomly choosed based on [this](http://www.iana.org/assignments/multicast-addresses/multicast-addresses.xhtml)) through the ``MulticastSocket``. 

The ``ReceiverThread`` class is always listening for packages on the same multicast address. If the package contains the bytes corresponding to ``hi``, then the sender IP address is added to a Map (key=IP address, value=current time in miliseconds).

As packages are received, the ``ReceiverThread`` prints the list of available hosts. The ``getAvailableHosts`` method calculates for each host if the last ``hi`` message is older than 10 seconds, if it is then the host is removed from the Map (i.e. the host is no longer available - a network outage, a shutdown or a crash happened, for instance).

# requirements

* Java SE 7
* Docker 1.9.1
* Maven 3.3.3

# running

```sh
# Compile
mvn clean install

# Run the docker container with java 7 pre-installed 
# (you should run more than once to see the list of
#  available hosts increasing)
sudo -s
docker run --rm -v "$PWD":/usr/src/myapp -w /usr/src/myapp java:7 java -jar target/example-1.0.jar
```

# more info

http://ecolabardini.blogspot.com.br/2015/12/playing-with-multicast-groups-in-java.html

# license

MIT License


