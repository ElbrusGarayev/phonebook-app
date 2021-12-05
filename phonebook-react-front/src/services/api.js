import axios from 'axios';

export const IS_PROD = process.browser && window?.location?.hostname === 'phonebook.aws.com';
export const IS_LOCAL = window?.location?.hostname === 'localhost';


export const prodUrl = 'https://phonebook.aws.com';
export const localUrl = 'http://localhost:8080/user';

export const baseUrl = () => {
    if (IS_PROD) return prodUrl;
    if (IS_LOCAL) return localUrl;
};


const api = axios.create({
    baseURL: baseUrl(),
    headers: { 
        "Content-Type": "application/json;charset=utf-8"
    }
});

export default api;