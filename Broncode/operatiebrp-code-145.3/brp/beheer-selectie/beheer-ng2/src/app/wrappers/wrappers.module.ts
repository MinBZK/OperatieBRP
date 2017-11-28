import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {DatepickerComponent} from './datepicker/datepicker.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {ReactiveFormsModule} from '@angular/forms';

@NgModule({
  imports: [
    CommonModule,
    NgbModule,
    ReactiveFormsModule
  ],
  declarations: [DatepickerComponent],
  exports: [DatepickerComponent]
})
export class WrappersModule {

}
