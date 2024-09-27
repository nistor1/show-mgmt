import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { authGuard } from './core/guard/authentication.guard';


export const routes: Routes = [
  {
    path: 'auth',
    loadChildren: () => import('./feature/authentication/authentication.module').then(m => m.AuthenticationModule),
    canActivate: [ authGuard ],
    data: {
      jwtTokenPresent: false,
      redirectUrl: '/dashboard/chefs'
    }
  },
  {
    path: 'auth',
    loadChildren: () => import('./feature/authentication/authentication.module').then(m => m.AuthenticationModule),
    canActivate: [ authGuard ],
    data: {
      jwtTokenPresent: false,
      redirectUrl: '/dashboard/client'
    }
  },
  {
    path: 'auth',
    loadChildren: () => import('./feature/authentication/authentication.module').then(m => m.AuthenticationModule),
    canActivate: [ authGuard ],
    data: {
      jwtTokenPresent: false,
      redirectUrl: '/dashboard/admin'
    }
  },
  {
    path: 'auth',
    loadChildren: () => import('./feature/authentication/authentication.module').then(m => m.AuthenticationModule),
    canActivate: [ authGuard ],
    data: {
      jwtTokenPresent: false,
      redirectUrl: '/dashboard/employee'
    }
  },
  {
    path: 'dashboard',
    loadChildren: () => import('./feature/dashboard/dashboard.module').then(m => m.DashboardModule),
    canActivate: [ authGuard ],
    data: {
      jwtTokenPresent: true,
      redirectUrl: '/auth/login'
    }
  },
  {
    path: '**',
    redirectTo: 'dashboard'
  }
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {
}
