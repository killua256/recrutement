import { BaseService } from '@shared/services/base.service';
import { User } from '@shared/types';

class UserService extends BaseService {
    private SRC_URL = "users/";

    constructor() {
        super();
    }

    static instance = new UserService();

    private httpUrl = (apiUrl: string) => {
        return `${this.SRC_URL}${apiUrl}`
    }

    findByUsername(username: string){
        return this.httpClient<User>({
            apiUrl: this.httpUrl(''),
            method: "GET",
            params: [username]
        });
    }

}

export default UserService.instance;