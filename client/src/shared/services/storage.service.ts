import { Settings, User } from "@shared/types";

class StorageService {
    private _SETTINGS = "settings";
    private _USER_DATA = "userData";
    private _LOGIN_CODE = "mfa";

    private static instance: StorageService;

    constructor() {
    }

    static getInstance() {
        if (this.instance == null) {
            this.instance = new StorageService()
        }
        return this.instance
    }

    setUserData(userData: User){
        localStorage.setItem(this._USER_DATA, JSON.stringify(userData))
    }

    getUserData(): User | null{
        const data = localStorage.getItem(this._USER_DATA)
        if(data){
            return JSON.parse(data)
        }
        return null
    }

    clearUserData(){
        localStorage.removeItem(this._USER_DATA)
    }

    setSettings(userData: Settings){
        localStorage.setItem(this._SETTINGS, JSON.stringify(userData))
    }

    getSettings(): Settings | null{
        const data = localStorage.getItem(this._SETTINGS)
        if(data){
            return JSON.parse(data)
        }
        return null
    }

    clearSettings(){
        localStorage.removeItem(this._SETTINGS)
    }

    setLoginCode(code: number){
        localStorage.setItem(this._LOGIN_CODE, JSON.stringify(code))
    }

    getLoginCode(): number | null{
        const data = localStorage.getItem(this._LOGIN_CODE)
        if(data){
            return +data
        }
        return null
    }

    clearLoginCode(){
        localStorage.removeItem(this._LOGIN_CODE)
    }


}

export default StorageService.getInstance();

