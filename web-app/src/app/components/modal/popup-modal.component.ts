import {
  Component,
  Directive,
  Input,
  TemplateRef,
  ViewChild,
} from '@angular/core';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { LoginComponent } from '../login/login.component';
import { RegisterComponent } from '../register/register.component';
//import { RegisterComponent } from '../register/register.component';

@Component({
  selector: 'ngbd-modal-component',
  templateUrl: './popup-modal.component.html',
})
export class NgbdModalComponent {
  constructor(private modalService: NgbModal) {}

  open() {
    const modalRef = this.modalService.open(LoginComponent);
    //const regModalRef = this.modalService.open(RegisterComponent);
    modalRef.componentInstance.name = 'Login';
    // regModalRef.componentInstance.name = 'Register';
  }
}
