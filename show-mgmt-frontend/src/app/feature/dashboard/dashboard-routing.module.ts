import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {hasRole} from '../../core/guard/authorization.guard';
import {InvalidAccessComponent} from '../../shared/components/invalid-access/invalid-access.component';
import {NotFoundComponent} from '../../shared/components/not-found/not-found.component';
import {ChefComponent} from './chef/chef.component';
import {ChefsComponent} from './chefs/chefs.component';
import {ClientComponent} from "./client/client.component";
import {EmployeeComponent} from "./employee/employee.component";
import {AdminComponent} from "./admin/admin.component";
import {ShowComponent} from "./show/show.component";
import {UserComponent} from "./user/user.component";
import {CreateShowComponent} from "./create-show/create-show.component";
import {CreateUserComponent} from "./create-user/create-user.component";
import {OrderCardComponent} from "./order-card/order-card.component";
import {OrdersComponent} from "./orders/orders.component";
import {OrderComponent} from "./order/order.component";


export const routes: Routes = [
  {
    path: 'client',
    component: ClientComponent,
    canActivate: [hasRole],
    data: {
      requiredRoles: ['ADMIN', 'EMPLOYEE', 'CLIENT']
    }
  },
  {
    path: 'employee',
    component: EmployeeComponent,
    canActivate: [hasRole],
    data: {
      requiredRoles: ['ADMIN', 'EMPLOYEE']
    }
  },
  {
    path: 'admin',
    component: AdminComponent,
    canActivate: [hasRole],
    data: {
      requiredRoles: ['ADMIN']
    }
  },
  {
    path: 'shows',
    component: ShowComponent,
    canActivate: [hasRole],
    data: {
      requiredRoles: ['CLIENT', 'EMPLOYEE', 'ADMIN']
    }
  },
  {
    path: 'show/:idShow',
    component: ShowComponent,
    canActivate: [hasRole],
    data: {
      requiredRoles: ['CLIENT', 'EMPLOYEE', 'ADMIN']
    }
  },
  {
    path: 'create-show',
    component: CreateShowComponent,
    canActivate: [hasRole],
    data: {
      requiredRoles: ['EMPLOYEE', 'ADMIN']
    }
  },
  {
    path: 'create-show/:idShow',
    component: CreateShowComponent,
    canActivate: [hasRole],
    data: {
      requiredRoles: ['EMPLOYEE', 'ADMIN']
    }
  },
  {
    path: 'create-user',
    component: CreateUserComponent,
    canActivate: [hasRole],
    data: {
      requiredRoles: ['ADMIN']
    }
  },
  {
    path: 'create-user/:idUser',
    component: CreateUserComponent,
    canActivate: [hasRole],
    data: {
      requiredRoles: ['ADMIN']
    }
  },
  {
    path: 'user/:idUser',
    component: UserComponent,
    canActivate: [hasRole],
    data: {
      requiredRoles: ['ADMIN']
    }
  },
  {
    path: 'users',
    component: UserComponent,
    canActivate: [hasRole],
    data: {
      requiredRoles: ['ADMIN']
    }
  },
  {
  path: 'order/:orderId',
  component: OrderComponent,
  canActivate: [hasRole],
  data: {
  requiredRoles: ['ADMIN', 'EMPLOYEE', 'CLIENT']
}
},
{
  path: 'order',
    component: OrderComponent,
  canActivate: [hasRole],
  data: {
    requiredRoles: ['ADMIN', 'EMPLOYEE', 'CLIENT']
}
},
  {
    path: 'orders',
    component: OrdersComponent,
    canActivate: [hasRole],
    data: {
      requiredRoles: ['ADMIN', 'EMPLOYEE', 'CLIENT']
    }
  },
  {
    path: 'order-card/:idOrder',
    component: OrderCardComponent,
    canActivate: [hasRole],
    data: {
      requiredRoles: ['ADMIN', 'EMPLOYEE', 'CLIENT']
    }
  },
  {
    path: 'order-card',
    component: OrderCardComponent,
    canActivate: [hasRole],
    data: {
      requiredRoles: ['ADMIN', 'EMPLOYEE', 'CLIENT']
    }
  },
  {
    path: 'comment-card/:idComment',
    component: UserComponent,
    canActivate: [hasRole],
    data: {
      requiredRoles: ['ADMIN', 'EMPLOYEE']
    }
  },
  {
    path: 'comment-card',
    component: UserComponent,
    canActivate: [hasRole],
    data: {
      requiredRoles: ['ADMIN', 'EMPLOYEE', 'CLIENT']
    }
  },
  {
    path: 'invalid-access',
    component: InvalidAccessComponent
  },
  {
    path: 'not-found',
    component: NotFoundComponent
  },
  {
    path: '**',
    redirectTo: 'chefs'
  }
];

@NgModule({
  imports: [
    InvalidAccessComponent,
    NotFoundComponent,
    RouterModule.forChild(routes)
  ],
  exports: [RouterModule]
})
export class DashboardRoutingModule {
}
