import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DiscussionsListElemComponent } from './discussions-list-elem.component';

describe('DiscussionsListComponent', () => {
  let component: DiscussionsListElemComponent;
  let fixture: ComponentFixture<DiscussionsListElemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DiscussionsListElemComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DiscussionsListElemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
