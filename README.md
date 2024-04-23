# Weather App


# Contact

Tomáš Szyszkowicz

email: tomas.szyszkowicz@profiq.com

phone-number: +420606633440

# Description

Simple weather app using weatherstack API.

# Usage

## Cloning the project

You can clone the project using this:

```bash
git git@github.com:tomasszyszkowicz/FunGames.git
```

Remember that you need to setup the SSH key on your machine.

Do this to navigate to the root of the project:

```bash
cd FunGames
```

## Start the app

You can start the app using this command:

```bash
./mvnw spring-boot:run
```

You can check if the app is running, on: http://127.0.0.1:8080/

# Deploy

## Manual Deploy

If you want to manually deploy the app to heroku, you can do it by running this script:

```bash
./deploy.sh
```

Remember, that you need to login via heroku CLI to make this work

## Github Actions Deploy

The app is automatically deployed to heroku on every push to 'prod' branch. Look inside the '.github/workflows' directory to see the 'deploy.yml' file


