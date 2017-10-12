#!/bin/bash
sudo pip install python python-pip python-setuptools
sudo pip install -r docs/requirements.txt
make -C docs/ html