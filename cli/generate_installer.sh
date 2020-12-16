#!/bin/bash

mkdir -p build/installer
cp build/installer.sh build/installer/installer.sh
echo "* Compressing archive"
tar cJf build/stml.tar.xz -C build/libs/ .
echo "* Appending to installer"
cat build/stml.tar.xz >> build/installer/installer.sh
chmod +x build/installer/installer.sh
