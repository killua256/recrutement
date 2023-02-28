import authRoutes from "@modules/auth/routes";
import homeRoutes from "@modules/home/routes";
import userRoutes from "@modules/user/routes";


const appRoutes: any[] = [
    ...authRoutes,
    ...homeRoutes,
    ...userRoutes
];

export default appRoutes;