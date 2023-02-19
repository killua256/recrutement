import Axios, { Method } from "axios";
import { Config } from "@config/Config";
import storageService from "./storage.service";
import i18next from "i18next";
import { HttpRequest, HttpResponse } from "@shared/types";

export class BaseService {
    protected API_URL = Config.getConfig().apiUrl;

    constructor() {
        Axios.defaults.withCredentials = true;
        this.interceptToken(() => {
            storageService.clearUserData()
            window.location.reload()
        })
    }

    private interceptToken(logout: any) {
        Axios.interceptors.response.use(
            (res) => res,
            (error) => {
                console.log("ERROR : ", error)
                if (
                    error.config &&
                    error.response?.status === 401 &&
                    //error.response?.data?.message === "token_expired" &&
                    !error.config.__isRetry
                ) {
                    return new Promise((resolve, reject) => {
                        this.refreshToken(error.config, logout)
                            .then((result) => {
                                resolve(result);
                            })
                            .catch((err) => {
                                logout()
                                reject(err);
                            });
                    });
                }
                logout();
                return Promise.reject(error);
            });
    }

    private refreshToken(config: any, logout: any) {
        config.__isRetry = true
        return new Promise((resolve, reject) => {
            this.refresh()
                .then((res: any) => {
                    Axios
                        .request(config)
                        .then((result) => {
                            return resolve(result);
                        })
                        .catch((err) => {
                            return reject(err);
                        });
                })
                .catch((err) => {
                    logout();
                });
        });
    };

    private async refresh(){
        return this.httpClient({
            apiUrl: "auth/refresh-token",
            method: "POST"
        })
    }

    private createParams = (url: string, params: any[]) => {
        return url.endsWith('/') ? params.join('/') : '/' + params.join('/')
    }

    private createQuery = (query: any) => {
        let url = ""
        if (Object.keys(query).length > 0) {
            Object.keys(query).forEach((key, i) => {
                if (query[key] != undefined && query[key].toString().length > 0) {
                    if (url === "") {
                        url += `?${key}=${query[key]}`
                    } else {
                        url += `&${key}=${query[key]}`
                    }
                }
            });
        }
        return url;
    }


    protected async httpClient<T>({
        apiUrl,
        method,
        body,
        params,
        query,
        headers,
        isFile = false
    }: HttpRequest): Promise<HttpResponse<T>> {
        let url = `${this.API_URL}${apiUrl}`
        if (params && params.length > 0) {
            url += this.createParams(url, params)
        }
        if (query && Object.keys(query).length > 0) {
            url += this.createQuery(query)
        }
        const currentDate = new Date()
        const options = {
            url,
            method: method,
            data: body || null,
            headers: {
                Accept: "application/json",
                "Content-Type": isFile ?
                    'multipart/form-data;charset=utf-8' :
                    'application/json;charset=UTF-8',
                "Access-Control-Allow-Credentials": "true",
                "timeZone": currentDate.getTimezoneOffset()/60 * (-1),
                ...headers
            }
        };

        try {
            const {data} = await Axios.request<T>(options);
            return {
                success: true,
                response: data,
                error: null
            }
        } catch (error: any) {
            let errorMessage = error.response?.data?.message || 'server_error';
            if (typeof error.response?.data?.message !== 'string') {
                errorMessage = 'server_error';
            }
            if (!navigator.onLine) {
                errorMessage = "connection_error"
            }
            return {
                success: false,
                response: null,
                error: {
                    status: error.response?.status || 500,
                    message: errorMessage//i18next.t(`errors.${errorMessage}`)
                }
            }
        }
    }
}