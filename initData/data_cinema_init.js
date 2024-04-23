const axios = require('axios');

const BASE_URL = "http://localhost:8080/api/";

const HEADERS = { "Content-Type": "application/json; charset=utf-8", "accept": "*/*" };
const COUNTRY = ["Russia", "USA", "Turkey", "Republic Korea", "Japan"];
const GENRE = ["Action", "Fantasy", "Romance", "Thriller", "Horror"];
const RATING = [0,3,6,12,16,18];
const FAMILIES = ["Petrov", "Ivanov", "Semyonov", "Egorov", "Smirnov"];
const NAMES = ["Sergey", "Ivan", "Vasiliy", "Igor", "Dmitriy"];
const PATRONUMIC = ["Petrovich", "Alexsandrovich", "Dmitrievich", "Andreevich", "Ivanovich"];

const getRandomInt = (min, max) => {
    return Math.floor(Math.random() * (max - min + 1)) + min;
}

const getRandomDate = () => {
    return Math.random() * 5 * 24 * 60 * 60 * 1000;
}

const addMovie = async () => {
    for (let i = 0; i < 20; i++) {
        const index = getRandomInt(0, 4);
        const title = "Movie" + i;
        const createDate = new Date(Date.now() + getRandomDate());
        const rating = RATING[getRandomInt(0, 5)];
        const duration = getRandomInt(60, 180);
        const dto = {
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

const addReviewInMovie = async () => {
    for (let i = 0; i < 100; i++) {
        let movies_arr = []
        await axios.get(BASE_URL + "movies", { headers: HEADERS })
            .then(response => {
                movies_arr = response.data;
            })
            .catch(error => {
                console.error('Ошибка при получении списка фильмов:', error);
            });
        let film_critics_arr = []
        await axios.get(BASE_URL + "filmCritics", { headers: HEADERS })
            .then(response => {
                film_critics_arr = response.data;
            })
            .catch(error => {
                console.error('Ошибка при получении списка фильмов:', error);
            });
        const movies = movies_arr[getRandomInt(0, movies_arr.length - 1)].movieUUID;
        const filmCritics = film_critics_arr[getRandomInt(0, film_critics_arr.length - 1)].filmCriticUUID;
        const date = new Date(Date.now() + getRandomDate());
        const estimation = getRandomInt(0,5);
        const comment = "Comment" + i;
        const dto = {
            "date": date.toISOString().split('T')[0],
            "estimation": estimation,
            "comment": comment,
            "movieUUID": movies,
            "filmCriticUUID": filmCritics
        };
        try {
            const response = await axios.post(BASE_URL + "reviews/addReviewInMovie", dto, { headers: HEADERS });
            console.log(response.data);
        } catch (error) {
            console.error(error);
        }
    }
}

(async ()=>{
	await addMovie();
	await addFilmCritic();
	await addReviewInMovie();

})()
