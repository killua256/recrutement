import authRoutes from "@modules/auth/routes";
import homeRoutes from "@modules/home/routes";
import settingsRoutes from "@modules/settings/routes";


const appRoutes: any[] = [
    ...authRoutes,
    ...homeRoutes,
    ...settingsRoutes
];

export default appRoutes;