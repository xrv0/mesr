<h1 align="center">Welcome to mesr 👋</h1>
<p>
  <img alt="Version" src="https://img.shields.io/badge/version-0.1-blue.svg?cacheSeconds=2592000" />
  <a href="https://www.gnu.org/licenses/gpl-3.0.de.html" target="_blank">
    <img alt="License: GNU GPLv3" src="https://img.shields.io/badge/License-GNU GPLv3-yellow.svg" />
  </a>
  <a href="https://twitter.com/xrvzero" target="_blank">
    <img alt="Twitter: xrvzero" src="https://img.shields.io/twitter/follow/xrvzero.svg?style=social" />
  </a>
</p>

> An open source decentralized anonymous end-to-end encrypted instant messenger

## Basic Concept
<img src=network.jpg></img>

When Alice wants to send a message to Bob the message is first encrypted with PubK^B, PubK^AMessagingServer, PubK^ARelay1 and then with PubK^ARelay0.
It is then send to ARelay0 which decrypts it with PrvK^ARelay0 and sends it to the ARelay1. ARelay1 then decrypts it with PrvK^ARelay1 and relays it to AMessagingServer.
AMessagingServer saves it in his SQL databank and forwards 
## Author

👤 **xrv0 and Godlycrusader*o*

* Twitter: [@xrvzero](https://twitter.com/xrvzero)
* Github: [@xrv0](https://github.com/xrv0)

## 🤝 Contributing

Contributions, issues and feature requests are welcome!<br />Feel free to check [issues page](https://github.com/xrv0/mesr/issues).

## Show your support

Give a ⭐️ if this project helped you!

## 📝 License

Copyright © 2019 [xrv0 and Godlycrusader](https://github.com/xrv0).<br />
This project is [GNU GPLv3](https://www.gnu.org/licenses/gpl-3.0.de.html) licensed.

## ⚠️ Important

This project is still under heavy development and should not be used in a production enviroment
