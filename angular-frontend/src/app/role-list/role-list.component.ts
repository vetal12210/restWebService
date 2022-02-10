import {Component, OnInit} from '@angular/core';
import {Role} from "../role";
import {RoleService} from "../role.service";

@Component({
  selector: 'app-role-list',
  templateUrl: './role-list.component.html',
  styleUrls: ['./role-list.component.css']
})
export class RoleListComponent implements OnInit {
  roles: Role[];

  constructor(private roleService: RoleService) {
  }

  ngOnInit(): void {
    this.getRoles();
  }

  private getRoles() {
    this.roleService.getRoleList().subscribe(data => {
      this.roles = data;
    })
  }
}
