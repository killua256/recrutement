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

    update(id: number, data: Partial<User>){
        return this.httpClient<User>({
            apiUrl: this.httpUrl(''),
            method: "PUT",
            params: [id],
            body: data
        });
    }

    updateAvatar(avatar: FormData){
        return this.httpClient<User>({
            apiUrl: this.httpUrl('avatar'),
            method: "POST",
            body: avatar,
            isFile: true
        });
    }

    updateCover(cover: FormData){
        return this.httpClient<User>({
            apiUrl: this.httpUrl('cover'),
            method: "POST",
            body: cover,
            isFile: true
        });
    }

}

export default UserService.instance;
