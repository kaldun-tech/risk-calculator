cd ..

./gradlew compileJava
ret=$?
if [ $ret -ne 0 ]; then
exit $ret
fi
rm -rf build

rm -rf target

./gradlew build
ret=$?
if [ $ret -ne 0 ]; then
exit $ret
fi

rm -rf build
exit
