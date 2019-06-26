FROM alpine:3.2

RUN apk update
RUN apk upgrade
RUN apk add curl wget bash
RUN apk add ruby ruby-bundler
RUN rm -rf /var/cache/apk/*

RUN mkdir /usr/app
WORKDIR /usr/app

COPY Gemfile /usr/app/
COPY Gemfile.lock /usr/app/
RUN bundle install

COPY . /usr/app
