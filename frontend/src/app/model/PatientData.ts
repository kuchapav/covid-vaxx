import { InsuranceCompany } from '@app/model/InsuranceCompany';
import { AnsweredQuestion } from '@app/model/AnsweredQuestion';

export interface PatientData {
  firstName: string;
  lastName: string;
  personalNumber: string;
  email: string;
  phoneNumber: string;
  district: string;
  zipCode: string;
  insuranceCompany?: InsuranceCompany;
  questionnaire: AnsweredQuestion[];

  verifiedOn?: Date;
  vaccinatedOn?: Date;
  isNonDominantHandUsed?: boolean;
}
