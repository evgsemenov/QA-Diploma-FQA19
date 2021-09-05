FROM node:erbium-alpine3.14
WORKDIR /app
RUN npm install
COPY . .
RUN npm install
CMD ["npm", "start"]
EXPOSE 9999