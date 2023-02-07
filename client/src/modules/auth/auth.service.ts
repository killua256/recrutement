import { BaseService } from '@shared/services/base.service';
import { User } from '@shared/types';

class AuthService extends BaseService {
    private SRC_URL = "auth/";

    constructor() {
        super();
    }

    static instance = new AuthService();

    private httpUrl = (apiUrl: string) => {
        return `${this.SRC_URL}${apiUrl}`
    }

    authenticate(data?: any){
        return this.httpClient<User | number>(this.httpUrl('signin'), "POST", data);
    }

    signup(data?: any){
        return this.httpClient(this.httpUrl('signup'), 'POST', data);
    }

}

export default AuthService.instance;
