import { Route } from '@shared/types';
import { lazy } from 'react';

const UserProfile = lazy(() => import('../components/user-profile/UserProfile'));

const settingsRoutes: Route[] = [
    {
        path: '/in/:username',
        component: UserProfile
    }
    
]

export default settingsRoutes;