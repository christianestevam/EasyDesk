FROM ubuntu:latest
LABEL authors="chris"

ENTRYPOINT ["top", "-b"]