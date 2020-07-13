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

## Basic Networking Concept
<img src=network.jpg></img>

## Password Encryption
<img src="password.jpg"></img>

### Signup:
The password choosen by the user is hashed with bcrypt on clientside and then sent to the server.
The server then hashes the password again and stores it in their databank

### Login

The password is hashed on clientside and sent to the server.
The server then compares the bcrypt hash of the received string with the one stored in the database

## Author

👤 **xrv0 and Godlycrusader*o*

* Twitter: [@xrvzero](https://twitter.com/xrvzero)
* Github: [@xrv0](https://github.com/xrv0)
* Twitter: [@GodlyYee](https://twitter.com/GodlyYee)
* Github: [@Godlycrusader](https://github.com/Godlycrusader)

## 🤝 Contributing

Contributions, issues and feature requests are welcome!<br />Feel free to check [issues page](https://github.com/xrv0/mesr/issues).

## Show your support

Give a ⭐️ if this project helped you!

## 📝 License

Copyright © 2019 [xrv0 and Godlycrusader](https://github.com/xrv0).<br />
This project is [GNU GPLv3](https://www.gnu.org/licenses/gpl-3.0.de.html) licensed.

## ⚠️ Important

This project is still under heavy development and should not be used in a production enviroment
