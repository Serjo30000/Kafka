const axios = require('axios');

const BASE_URL = "http://localhost:8082/api/";

const HEADERS = { "Content-Type": "application/json; charset=utf-8", "accept": "*/*" };
const COUNTRY = ["Russia", "USA", "Turkey", "Republic Korea", "Japan"];
const GENRE = ["Action", "Fantasy", "Romance", "Thriller", "Horror"];
const RATING = [0,3,6,12,16,18];
const FAMILIES = ["Petrov", "Ivanov", "Semyonov", "Egorov", "Smirnov"];
const NAMES = ["Sergey", "Ivan", "Vasiliy", "Igor", "Dmitriy"];
const PATRONUMIC = ["Petrovich", "Alexsandrovich", "Dmitrievich", "Andreevich", "Ivanovich"];

let imdbs_arr = [];
let logins_arr = [];

const getRandomInt = (min, max) => {
    return Math.floor(Math.random() * (max - min + 1)) + min;
}

const getRandomDate = () => {
    return Math.random() * 5 * 24 * 60 * 60 * 1000;
}

let usedIds = new Set();

function generateIMDbId() {
    let characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    let imdbIdLength = 7;
    let imdbId = 'tt';

    do {
        imdbId = 'tt';
        for (let i = 0; i < imdbIdLength; i++) {
            imdbId += characters.charAt(Math.floor(Math.random() * characters.length));
        }
    } while (usedIds.has(imdbId));

    usedIds.add(imdbId);
    return imdbId;
}

const addMovie = async () => {
    for (let i = 0; i < 20; i++) {
        const index = getRandomInt(0, 4);
        const imdb = generateIMDbId()
        imdbs_arr.push(imdb);
        const title = "Movie" + i;
        const createDate = new Date(Date.now() + getRandomDate());
        const rating = RATING[getRandomInt(0, 5)];
        const duration = getRandomInt(60, 180);
        const dto = {
            "imdb": imdb,
            "title": title,
            "createDate": createDate.toISOString().split('T')[0],
            "country": COUNTRY[index],
            "genre": GENRE[index],
            "rating": rating,
            "duration": duration
        };
        try {
            const response = await axios.post(BASE_URL + "movies/addMovie", dto, { headers: HEADERS });
            console.log(response.data);
        } catch (error) {
            console.error(error);
        }
    }
}

const addFilmCritic = async () => {
    for (let i = 0; i < 20; i++) {
        const index = getRandomInt(0, 4);
        const login = "Login" + i;
        logins_arr.push(login);
        const fio = {
            "family": FAMILIES[index],
            "name": NAMES[index],
            "patronymic": PATRONUMIC[index]
        };
        const dateRegistration = new Date(Date.now() + getRandomDate());
        const dto = {
            "login": login,
            "fio": fio,
            "dateRegistration": dateRegistration.toISOString().split('T')[0]
        };
        try {
            const response = await axios.post(BASE_URL + "filmCritics/addFilmCritic", dto, { headers: HEADERS });
            console.log(response.data);
        } catch (error) {
            console.error(error);
        }
    }
}

const addReview = async () => {
    for (let i = 0; i < 100; i++) {
        const imdbs = imdbs_arr[getRandomInt(0, imdbs_arr.length - 1)];
        const logins = logins_arr[getRandomInt(0, logins_arr.length - 1)];
        const date = new Date(Date.now() + getRandomDate());
        const estimation = getRandomInt(0,5);
        const comment = "Comment" + i;
        const dto = {
            "date": date.toISOString().split('T')[0],
            "estimation": estimation,
            "comment": comment,
            "imdb": imdbs,
            "login": logins
        };
        try {
            const response = await axios.post(BASE_URL + "reviews/addReview", dto, { headers: HEADERS });
            console.log(response.data);
        } catch (error) {
            console.error(error);
        }
    }
}

(async ()=>{
	await addMovie();
	await addFilmCritic();
	await addReview();

})()
