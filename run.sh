#!/bin/sh
export PORT=5000
java -cp target/classes:target/dependency/* com.github.steingrd.immensebastion.App
