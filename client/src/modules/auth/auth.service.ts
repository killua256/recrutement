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

    authenticate(data: any){
        return this.httpClient<User | number>({
            apiUrl: this.httpUrl('signin'),
            method: "POST",
            body: data
        });
    }

    mfaAuth(data: string){
        return this.httpClient<User>({
            apiUrl: this.httpUrl('signin-mfa'),
            method: "POST",
            body: data
        });
    }

    resendMfaCode(id: number){
        return this.httpClient<number>({
            apiUrl: this.httpUrl('resend-code/'),
            method: "POST",
            params: [id]
        });
    }

    signup(data: any){
        return this.httpClient<User>({
            apiUrl: this.httpUrl('signup'),
            method: "POST",
            body: data
        });
    }

}

export default AuthService.instance;
