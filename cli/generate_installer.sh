#!/bin/bash

mkdir -p build/installer
cp build/installer.sh build/installer/installer-linux64.sh
echo "* Compressing linux64 binary"
tar cJf build/stml.tar.xz -C build/graal/ .
echo "* Appending to installer-linux64.sh"
cat build/stml.tar.xz >> build/installer/installer-linux64.sh
chmod +x build/installer/installer-linux64.sh

cp build/installer.sh build/installer/installer-independent.sh
echo "* Compressing platform independent binaries"
tar cJf build/stml.tar.xz -C build/libs/ .
echo "* Appending to installer-independent.sh"
cat build/stml.tar.xz >> build/installer/installer-independent.sh
chmod +x build/installer/installer-independent.sh
