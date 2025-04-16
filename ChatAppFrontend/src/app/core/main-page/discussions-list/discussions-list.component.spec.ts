import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DiscussionsListComponent } from './discussions-list.component';

describe('DiscussionsMenuComponent', () => {
  let component: DiscussionsListComponent;
  let fixture: ComponentFixture<DiscussionsListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DiscussionsListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DiscussionsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
