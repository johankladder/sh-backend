#!/bin/bash

if [[ $TRAVIS_BRANCH == 'develop' ]]
  mvn heroku:deploy
else
  ls
fi