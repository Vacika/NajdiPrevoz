import {DataTableColumn} from "../interfaces/data-table-column.interface";
import {DataTableColumnType} from "../interfaces/enums/column-type.enum";

export const adminTableColumns = [
	{name: 'username', translationLabel: 'USERNAME', type: DataTableColumnType.TEXT, sortable: true} as DataTableColumn,
	{name: 'birthDate', translationLabel: 'BIRTH_DATE', type: DataTableColumnType.DATE, sortable: true} as DataTableColumn,
	{name: 'phoneNumber', translationLabel: 'PHONE_NUMBER', type: DataTableColumnType.NUMBER, sortable: true} as DataTableColumn,
	{name: 'firstName', translationLabel: 'FIRST_NAME', type: DataTableColumnType.TEXT, sortable: true} as DataTableColumn,
	{name: 'lastName', translationLabel: 'LAST_NAME', type: DataTableColumnType.TEXT, sortable: true} as DataTableColumn,
	{
		name: 'defaultLanguage',
		translationLabel: 'DEFAULT_LANGUAGE',
		type: DataTableColumnType.TEXT,
		sortable: true
	} as DataTableColumn,
	{name: 'registeredOn', translationLabel: 'REGISTERED_ON', type: DataTableColumnType.DATE, sortable: true} as DataTableColumn,
	{name: 'isActivated', translationLabel: 'IS_ACTIVATED', type: DataTableColumnType.BOOLEAN, sortable: true} as DataTableColumn,
	{name: 'isBanned', translationLabel: 'IS_BANNED', type: DataTableColumnType.BOOLEAN, sortable: true} as DataTableColumn,
	{
		name: 'adminActions',
		translationLabel: 'ACTIONS',
		type: DataTableColumnType.CUSTOM,
		subType: 'adminActions',
		sortable: true
	} as DataTableColumn]


export const tableColumnsForSent = [
	{name: 'allowedActions', translationLabel: 'ACTIONS', type: DataTableColumnType.CUSTOM, sortable: true} as DataTableColumn,
	{name: 'fromLocation', translationLabel: 'FROM', type: DataTableColumnType.TEXT, sortable: true} as DataTableColumn,
	{name: 'toLocation', translationLabel: 'TO', type: DataTableColumnType.TEXT, sortable: true} as DataTableColumn,
	{
		name: 'departureTime',
		translationLabel: 'DEPARTURE_TIME',
		type: DataTableColumnType.DATETIME,
		sortable: true
	} as DataTableColumn,
	{
		name: 'requestStatus',
		translationLabel: 'REQUEST_STATUS',
		type: DataTableColumnType.CUSTOM,
		sortable: true
	} as DataTableColumn,
	{name: 'driverName', translationLabel: 'DRIVER_NAME', type: DataTableColumnType.TEXT, sortable: true} as DataTableColumn,
	{name: 'tripId', translationLabel: 'TRIP_ID', type: DataTableColumnType.NUMBER, sortable: true} as DataTableColumn,
	{name: 'rideStatus', translationLabel: 'RIDE_STATUS', type: DataTableColumnType.CUSTOM, sortable: true} as DataTableColumn]


export const tableColumnsForReceived = [
	{name: 'allowedActions', translationLabel: 'ACTIONS', type: DataTableColumnType.CUSTOM, sortable: true} as DataTableColumn,
	{name: 'fromLocation', translationLabel: 'FROM', type: DataTableColumnType.TEXT, sortable: true} as DataTableColumn,
	{name: 'toLocation', translationLabel: 'TO', type: DataTableColumnType.TEXT, sortable: true} as DataTableColumn,
	{
		name: 'departureTime',
		translationLabel: 'DEPARTURE_TIME',
		type: DataTableColumnType.DATETIME,
		sortable: true
	} as DataTableColumn,
	{
		name: 'requestStatus',
		translationLabel: 'REQUEST_STATUS',
		type: DataTableColumnType.CUSTOM,
		sortable: true
	} as DataTableColumn,
	{name: 'tripId', translationLabel: 'TRIP_ID', type: DataTableColumnType.NUMBER, sortable: true} as DataTableColumn,
	{name: 'rideStatus', translationLabel: 'RIDE_STATUS', type: DataTableColumnType.CUSTOM, sortable: true} as DataTableColumn];

export const tableColumnsAsDriver = [
	{name: 'allowedActions', translationLabel: 'ACTIONS', type: DataTableColumnType.CUSTOM, sortable: true} as DataTableColumn,
	{name: 'from', translationLabel: 'FROM', type: DataTableColumnType.TEXT, sortable: true} as DataTableColumn,
	{name: 'to', translationLabel: 'TO', type: DataTableColumnType.TEXT, sortable: true} as DataTableColumn,
	{
		name: 'departureTime',
		translationLabel: 'DEPARTURE_TIME',
		type: DataTableColumnType.DATETIME,
		sortable: true
	} as DataTableColumn,
	{
		name: 'totalSeats',
		translationLabel: 'TOTAL_SEATS',
		type: DataTableColumnType.CUSTOM,
		sortable: true
	} as DataTableColumn,
	{name: 'pricePerHead', translationLabel: 'PRICE_PER_HEAD', type: DataTableColumnType.NUMBER, sortable: true} as DataTableColumn,
	{name: 'status', translationLabel: 'STATUS', type: DataTableColumnType.CUSTOM, sortable: true} as DataTableColumn];

export const tableColumnsAsPassenger = [
	{name: 'from', translationLabel: 'FROM', type: DataTableColumnType.TEXT, sortable: true} as DataTableColumn,
	{name: 'to', translationLabel: 'TO', type: DataTableColumnType.TEXT, sortable: true} as DataTableColumn,
	{name: 'departureTime',	translationLabel: 'DEPARTURE_TIME',	type: DataTableColumnType.DATETIME, sortable: true } as DataTableColumn,
	{
		name: 'totalSeats',
		translationLabel: 'TOTAL_SEATS',
		type: DataTableColumnType.CUSTOM,
		sortable: true
	} as DataTableColumn,
	{name: 'pricePerHead', translationLabel: 'PRICE_PER_HEAD', type: DataTableColumnType.NUMBER, sortable: true} as DataTableColumn,
	{name: 'driver', translationLabel: 'DRIVER', type: DataTableColumnType.TEXT, sortable: true} as DataTableColumn,
	{name: 'status', translationLabel: 'status', type: DataTableColumnType.CUSTOM, sortable: true} as DataTableColumn];