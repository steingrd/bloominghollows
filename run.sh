#!/bin/sh
export PORT=5000
export DATABASE_URL=postgres://steingrd:pukkverk@127.0.0.1:5432/immensebastion
java -cp target/classes:target/dependency/* com.github.steingrd.immensebastion.App

