import { Suspense } from 'react'
import 'antd/dist/reset.css';
import './App.css'
import Layout from './layout/Layout';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import appRoutes from './routes';
import { GuestRoute, ProtectedRoute } from '@shared/guards';
import { PageLoading } from '@shared/components';
import { AppContext } from './contexts';
import "./i18n/i18n";

function App() {

  return (
    <BrowserRouter>
      <AppContext>
        <Layout>
          <Routes>
            {appRoutes.map((route, i) => {
              if (route.status == 'PROTECTED') {
                return (
                  <Route key={i} path={route.path} element={
                    <Suspense fallback={(<PageLoading />)}>
                      <ProtectedRoute roles={route.roles}>
                        <route.component />
                      </ProtectedRoute>
                    </Suspense>
                  } />
                )
              } else if (route.status == 'GUEST') {
                return (
                  <Route key={i} path={route.path} element={
                    <Suspense fallback={(<PageLoading />)}>
                      <GuestRoute>
                        <route.component />
                      </GuestRoute>
                    </Suspense>
                  } />
                )
              } else {
                return (
                  <Route key={i} path={route.path} element={
                    <Suspense fallback={(<PageLoading />)}>
                      <route.component />
                    </Suspense>
                  } />
                )
              }
            })}
          </Routes>
        </Layout>
      </AppContext>
    </BrowserRouter>
  )
}

export default App
