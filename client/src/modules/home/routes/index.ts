import { lazy } from 'react';
const Home = lazy(() => import('../components/Home'));

const homeRoutes: any[] = [
    {
        path: "/feed",
        component: Home
    }
    
]

export default homeRoutes;