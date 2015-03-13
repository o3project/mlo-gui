mlo-gui
==============
MLO-GUI is a GUI frontend application for mlo-net.

Supported platform
--------
- CPU architecture: x86 or x64
- Memory: 3GB or more
- HDD space: 1GB or more.
- Operation system: Windows 7 or 8.1


Preparing runtime environment
--------
The following software is required, so must have been installed 
beforehand.

- jdk-7
- mlo-net environment

In this document, it is assumed that mlo-net environment has been 
constructed as guest OS on Oracle VM VirtualBox, and then 
port-forwarding setting of 8080 port has been set to Oracle VM 
VirtualBox.


Getting started
--------

#### Building mlo-gui ####

mlo-gui should be built on mlo-net envionment because mlo-gui has the 
dependency on mlo-srv in mlo-net.

**After executing install.sh in mlo-net**, mlo-gui can be built with
the following commands.
```
$ cd /home/developer/
$ git clone https://github.com/o3project/mlo-gui.git
$ cd mlo-gui/
$ ./build.sh
...

$ 
```

If successfully built, binary files will be zipped to the following 
path.

 /home/developer/mlo-gui/mlo-client/target/mlo-client-1.0-bin.zip.

#### Transfering and launching mlo-gui ####

mlo-gui can be launched in the following steps.

1. Transfers *mlo-client-1.0-bin.zip* to Windows PC via SCP and so on, and then unzips it.
2. Double-clicks *mlo-client-start.bat* including in the unzipped directory.





