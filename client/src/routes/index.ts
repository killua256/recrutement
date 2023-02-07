import authRoutes from "@modules/auth/routes";
import homeRoutes from "@modules/home/routes";


const appRoutes: any[] = [
    ...authRoutes,
    ...homeRoutes
];

export default appRoutes;