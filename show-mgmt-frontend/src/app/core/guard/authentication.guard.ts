import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivateFn, Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';


export const authGuard: CanActivateFn = (route: ActivatedRouteSnapshot) => {
  const router: Router = inject(Router);
  const jwtTokenPresent: boolean = route.data['jwtTokenPresent'];
  const redirectUrl: string = route.data['redirectUrl'];
  const hasCookie: boolean = isCookiePresent();

  return jwtTokenPresent === hasCookie
    ? true
    : router.navigateByUrl(redirectUrl);
};

const isCookiePresent = (): boolean => {
  const cookieService = inject(CookieService);

  return cookieService.check('jwt-token');
};
